package me.jboss.flyway.extension;

import org.jboss.as.controller.AbstractRemoveStepHandler;
import org.jboss.as.controller.OperationContext;
import org.jboss.as.controller.OperationFailedException;
import org.jboss.dmr.ModelNode;
import org.jboss.msc.service.ServiceName;

class DataSourceNameRemove extends AbstractRemoveStepHandler {

  public static final DataSourceNameRemove INSTANCE = new DataSourceNameRemove();

  private DataSourceNameRemove() {}

  @Override
  protected void performRuntime(OperationContext context, ModelNode operation, ModelNode model)
      throws OperationFailedException {
    String suffix = context.getCurrentAddressValue();
    ServiceName name = DataSourceNameService.createServiceName(suffix);
    context.removeService(name);
  }
}
