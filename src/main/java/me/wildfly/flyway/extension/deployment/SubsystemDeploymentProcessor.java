package me.wildfly.flyway.extension.deployment;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.jboss.as.server.deployment.Attachments;
import org.jboss.as.server.deployment.DeploymentPhaseContext;
import org.jboss.as.server.deployment.DeploymentUnit;
import org.jboss.as.server.deployment.DeploymentUnitProcessingException;
import org.jboss.as.server.deployment.DeploymentUnitProcessor;
import org.jboss.as.server.deployment.module.ResourceRoot;
import org.jboss.logging.Logger;
import org.jboss.vfs.VirtualFile;

/**
 * The SubsystemDeploymentProcessor class handle database auto-migration during the deployment
 * process.
 *
 * @author lubo
 */
public class SubsystemDeploymentProcessor
    implements DeploymentUnitProcessor, DeploymentUnitProcessorConstants {

  /** The logger. */
  private static Logger LOGGER = Logger.getLogger(SubsystemDeploymentProcessor.class);

  /** {@inheritDoc} */
  @Override
  public void deploy(DeploymentPhaseContext phaseContext) throws DeploymentUnitProcessingException {
    ResourceRoot root = phaseContext.getDeploymentUnit().getAttachment(Attachments.DEPLOYMENT_ROOT);
    String rootName = root.getRootName().toLowerCase();
    VirtualFile migrationFolder = null;
    String persistenceXmlPath = null;

    if (rootName.endsWith(war)) {
      migrationFolder = root.getRoot().getChild(MIGRATION_FOLDER_WAR_PATH);
      persistenceXmlPath = PERSISTENCE_XML_WAR_PATH;
    } else if (rootName.endsWith(jar)) {
      migrationFolder = root.getRoot().getChild(MIGRATION_FOLDER_JAR_PATH);
      persistenceXmlPath = PERSISTENCE_XML_JAR_PATH;
    }

    if (migrationFolder.exists()) {
      LOGGER.info(
          "FLYWAY_EXTENSION: Migrating DB schema is initialized with provided migration directory.");
      DataSource dataSource = null;
      dataSource = getDatasource(root, persistenceXmlPath);
      if (dataSource != null) {
        Flyway flyway = new Flyway();
        flyway.setDataSource(dataSource);
        LOGGER.info("FLYWAY_EXTENSION: Starting migration.");
        flyway.migrate();
        LOGGER.info("FLYWAY_EXTENSION: Migration is finished.");
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  public void undeploy(DeploymentUnit context) {}

  /**
   * Gets the datasource from ResourceRoot by persistenceXmlPath.
   *
   * @param root the root
   * @return the datasource
   * @throws DeploymentUnitProcessingException
   */
  private DataSource getDatasource(ResourceRoot root, String persistenceXmlPath) {
    VirtualFile persistenceXml = root.getRoot().getChild(persistenceXmlPath);
    DataSource dataSource = null;
    try {
      InitialContext initialContext = new InitialContext();
      String datasourceName = null;
      if (persistenceXml.exists()) {
        String line;
        InputStream is = persistenceXml.openStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        while ((line = br.readLine()) != null) {
          if (line.contains(DATASOURCE_ELEMENT)) {
            datasourceName = line.replaceAll(DATASOURCE_REGEX, "").trim();
            LOGGER.info("FLYWAY_EXTENSION: Found \"" + datasourceName + "\" datasource name in "
                + persistenceXmlPath);
            break;
          }
        }
        if (datasourceName == null || datasourceName.isEmpty()) {
          LOGGER.info(
              "FLYWAY_EXTENSION: No datasource name could be found in provided persistence.xml, default datasource name will be used.");
          datasourceName = DEFAULT_DATASOURCE;
        }
      } else {
        LOGGER.info(
            "FLYWAY_EXTENSION: No persistence.xml is provided, default datasource name will be used.");
        datasourceName = DEFAULT_DATASOURCE;
      }
      if (datasourceName != null && !datasourceName.isEmpty()) {
        dataSource = (DataSource) initialContext.lookup(datasourceName);
        LOGGER.info("FLYWAY_EXTENSION: JNDI lookup successfully obtained \"" + datasourceName
            + "\" DataSource.");
      }
    } catch (Exception e) {
      LOGGER.error("Could not determine datasource for Flyway migration.", e);
    }
    return dataSource;
  }

}
