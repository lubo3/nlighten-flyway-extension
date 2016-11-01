package me.jboss.flyway.deployment;

import java.util.HashMap;
import java.util.Map;

import org.flywaydb.core.Flyway;
import org.jboss.as.server.AbstractDeploymentChainStep;
import org.jboss.as.server.deployment.Attachments;
import org.jboss.as.server.deployment.DeploymentPhaseContext;
import org.jboss.as.server.deployment.DeploymentUnit;
import org.jboss.as.server.deployment.DeploymentUnitProcessingException;
import org.jboss.as.server.deployment.DeploymentUnitProcessor;
import org.jboss.as.server.deployment.Phase;
import org.jboss.as.server.deployment.module.ResourceRoot;
import org.jboss.msc.service.ServiceController;
import org.jboss.msc.service.ServiceRegistry;
import org.jboss.vfs.VirtualFile;

import me.jboss.flyway.extension.DataSourceNameService;

/**
 * An example deployment unit processor that does nothing. To add more deployment processors copy
 * this class, and add to the {@link AbstractDeploymentChainStep}
 */
public class SubsystemDeploymentProcessor implements DeploymentUnitProcessor {

  /**
   * See {@link Phase} for a description of the different phases
   */
  public static final Phase PHASE = Phase.DEPENDENCIES;

  /**
   * The relative order of this processor within the {@link #PHASE}. The current number is large
   * enough for it to happen after all the standard deployment unit processors that come with JBoss
   * AS.
   */
  public static final int PRIORITY = 0x4000;

  private static Map<String, String> dataSourceMap = new HashMap<>();

  static {
    dataSourceMap.put("postgres", "jdbc:postgresql://localhost:5432/postgres");
  }

  @Override
  public void deploy(DeploymentPhaseContext phaseContext) throws DeploymentUnitProcessingException {
    String name = phaseContext.getDeploymentUnit().getName();
    ResourceRoot root = phaseContext.getDeploymentUnit().getAttachment(Attachments.DEPLOYMENT_ROOT);
    DataSourceNameService service = getFlywayService(phaseContext.getServiceRegistry(), name);
    if (service != null) {
      SecurityManager qwe = System.getSecurityManager();
      VirtualFile migrationFolder = root.getRoot().getChild("WEB-INF/classes/db/migration");
      if (migrationFolder.exists()) {
        Flyway flyway = new Flyway();
        flyway.setDataSource(dataSourceMap.get(service.getDataSourceName()), "postgres",
            "password");
        flyway.migrate();

      }
    }
  }

  @Override
  public void undeploy(DeploymentUnit context) {}

  private DataSourceNameService getFlywayService(ServiceRegistry registry, String name) {
    int last = name.lastIndexOf(".");
    String suffix = name.substring(last + 1);
    ServiceController<?> container = registry.getService(DataSourceNameService.createServiceName(suffix));
    if (container != null) {
      return (DataSourceNameService) container.getValue();
    }
    return null;
  }
}
