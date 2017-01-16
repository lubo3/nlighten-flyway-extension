package me.jboss.flyway.deployment;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.jboss.logging.Logger;
import org.jboss.as.server.deployment.Attachments;
import org.jboss.as.server.deployment.DeploymentPhaseContext;
import org.jboss.as.server.deployment.DeploymentUnit;
import org.jboss.as.server.deployment.DeploymentUnitProcessingException;
import org.jboss.as.server.deployment.DeploymentUnitProcessor;
import org.jboss.as.server.deployment.module.ResourceRoot;
import org.jboss.vfs.VirtualFile;

/**
 * A SubsystemDeploymentProcessor may perform a variety of tasks, including (but not limited to):
 * <ol>
 * <li>Parsing a deployment descriptor and adding it to the context</li>
 * <li>Reading a deployment descriptor's data and using it to produce deployment items</li>
 * <li>Replacing a deployment descriptor with a transformed version of that descriptor</li>
 * <li>Removing a deployment descriptor to prevent it from being processed</li>
 * </ol>
 * .
 *
 * @author lubo
 */
public class SubsystemDeploymentProcessor implements DeploymentUnitProcessor {

  private static Logger LOGGER = Logger.getLogger(SubsystemDeploymentProcessor.class);
  
  private static final String DEFAULT_DATASOURCE_NAME = "java:comp/DefaultDataSource";

  /** The migration folder path. */
  private static String MIGRATION_FOLDER_PATH = "WEB-INF/classes/db/migration";

  /** The persistence xml path. */
  private static String PERSISTENCE_XML_PATH = "src/main/resources/persistence.xml";

  /** The datasource element. */
  private static String DATASOURCE_ELEMENT = "jta-data-source";

  /** The datasource regex. */
  private static String DATASOURCE_REGEX = "</?jta-data-source>";

  /** {@inheritDoc} */
  @Override
  public void deploy(DeploymentPhaseContext phaseContext) throws DeploymentUnitProcessingException {
    ResourceRoot root = phaseContext.getDeploymentUnit().getAttachment(Attachments.DEPLOYMENT_ROOT);
    VirtualFile migrationFolder = root.getRoot().getChild(MIGRATION_FOLDER_PATH);
    if (migrationFolder.exists()) {
      DataSource dataSource = getDatasource(root);
      if (dataSource != null) {
        Flyway flyway = new Flyway();
        flyway.setDataSource(dataSource);
        flyway.migrate();
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  public void undeploy(DeploymentUnit context) {}

  /**
   * Gets the datasource.
   *
   * @param root the root
   * @return the datasource
   */
  private DataSource getDatasource(ResourceRoot root) {
    VirtualFile persistenceXml = root.getRoot().getChild(PERSISTENCE_XML_PATH);
    DataSource dataSource = null;
    try {
      InitialContext initialContext = new InitialContext();
      if (persistenceXml.exists()) {
        String line;
        InputStream is = persistenceXml.openStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String datasourceName = null;
        while ((line = br.readLine()) != null) {
          if (line.contains(DATASOURCE_ELEMENT)) {
            datasourceName = line.replaceAll(DATASOURCE_REGEX, "").trim();
            break;
          }
        }
        if (datasourceName != null && !datasourceName.isEmpty()) {
          dataSource = (DataSource) initialContext.lookup(datasourceName);
        } else {
            //JEE7 - no datasource=default datasource
            dataSource = (DataSource) initialContext.lookup(DEFAULT_DATASOURCE_NAME);
        }
      } else {
         LOGGER.warn("No JPA context marker found. Proceed with custom datasource.");
      }
    } catch (Exception e) {
      LOGGER.error("Could not determine datasource for Flyway migration", e);
    }
    return dataSource;
  }

}
