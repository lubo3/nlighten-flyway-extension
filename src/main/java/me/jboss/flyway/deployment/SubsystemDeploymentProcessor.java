package me.jboss.flyway.deployment;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
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
 * 
 * @author lubo
 */
public class SubsystemDeploymentProcessor implements DeploymentUnitProcessor {

  /** {@inheritDoc} */
  @Override
  public void deploy(DeploymentPhaseContext phaseContext) throws DeploymentUnitProcessingException {
    ResourceRoot root = phaseContext.getDeploymentUnit().getAttachment(Attachments.DEPLOYMENT_ROOT);
    try {
      InitialContext initialContext = new InitialContext();
      DataSource dataSource =
          (DataSource) initialContext.lookup("java:jboss/datasources/ExampleDS");
      VirtualFile migrationFolder = root.getRoot().getChild("WEB-INF/classes/db/migration");
      if (migrationFolder.exists()) {
        Flyway flyway = new Flyway();
        flyway.setDataSource(dataSource);
        flyway.migrate();
      }
    } catch (NamingException e) {
      e.printStackTrace();
    }
  }

  /** {@inheritDoc} */
  @Override
  public void undeploy(DeploymentUnit context) {}
}
