package me.wildfly.flyway.extension;

import org.jboss.as.controller.AbstractBoottimeAddStepHandler;
import org.jboss.as.controller.OperationContext;
import org.jboss.as.controller.OperationFailedException;
import org.jboss.as.controller.registry.Resource;
import org.jboss.as.server.AbstractDeploymentChainStep;
import org.jboss.as.server.DeploymentProcessorTarget;
import org.jboss.as.server.deployment.Phase;
import org.jboss.dmr.ModelNode;

import me.wildfly.flyway.extension.deployment.SubsystemDeploymentProcessor;

/**
 * The FlywaySubsystemAdd class is responsible for adding the extension subsystem resource.
 * 
 * @author lubo
 */
class FlywaySubsystemAdd extends AbstractBoottimeAddStepHandler {

  /** The Constant INSTANCE. */
  static final FlywaySubsystemAdd INSTANCE = new FlywaySubsystemAdd();

  /**
   * Instantiates a new flyway subsystem add.
   */
  private FlywaySubsystemAdd() {}


  /** {@inheritDoc} */
  @Override
  public void performBoottime(OperationContext context, ModelNode operation, Resource resource)
      throws OperationFailedException {

    // Add deployment processors here
    // Remove this if you don't need to hook into the deployers, or you can add as many as you like
    // see SubDeploymentProcessor for explanation of the phases
    context.addStep(new AbstractDeploymentChainStep() {
      public void execute(DeploymentProcessorTarget processorTarget) {
        processorTarget.addDeploymentProcessor(FlywayExtension.SUBSYSTEM_NAME, Phase.DEPENDENCIES,
            Phase.PARSE_EJB_INJECTION_ANNOTATION, new SubsystemDeploymentProcessor());

      }
    }, OperationContext.Stage.RUNTIME);
  }
}
