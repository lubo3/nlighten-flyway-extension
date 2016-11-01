package me.jboss.flyway.extension;

import org.jboss.as.controller.AbstractBoottimeAddStepHandler;
import org.jboss.as.controller.OperationContext;
import org.jboss.as.controller.OperationFailedException;
import org.jboss.as.controller.registry.Resource;
import org.jboss.as.server.AbstractDeploymentChainStep;
import org.jboss.as.server.DeploymentProcessorTarget;
import org.jboss.dmr.ModelNode;

import me.jboss.flyway.deployment.SubsystemDeploymentProcessor;

/**
 * Handler responsible for adding the subsystem resource to the model
 */
class FlywaySubsystemAdd extends AbstractBoottimeAddStepHandler {

  static final FlywaySubsystemAdd INSTANCE = new FlywaySubsystemAdd();

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
        processorTarget.addDeploymentProcessor(FlywayExtension.SUBSYSTEM_NAME,
            SubsystemDeploymentProcessor.PHASE, SubsystemDeploymentProcessor.PRIORITY,
            new SubsystemDeploymentProcessor());

      }
    }, OperationContext.Stage.RUNTIME);

  }
}
