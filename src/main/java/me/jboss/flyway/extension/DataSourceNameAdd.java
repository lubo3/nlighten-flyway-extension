package me.jboss.flyway.extension;

import static me.jboss.flyway.extension.DataSourceNameDefinition.PATH;

import org.jboss.as.controller.AbstractAddStepHandler;
import org.jboss.as.controller.OperationContext;
import org.jboss.as.controller.OperationFailedException;
import org.jboss.dmr.ModelNode;
import org.jboss.msc.service.ServiceController.Mode;
import org.jboss.msc.service.ServiceName;

class DataSourceNameAdd extends AbstractAddStepHandler {

  public static final DataSourceNameAdd INSTANCE = new DataSourceNameAdd();

  private DataSourceNameAdd() {
    super(PATH);// call super with parameters that this handler takes
  }


  @Override
  protected void performRuntime(OperationContext context, ModelNode operation, ModelNode model)
      throws OperationFailedException {
    String suffix = context.getCurrentAddressValue();
    // we use resolveModelAttribute to properly resolve any expressions
    // and to use the default value if otherwise undefined
    String dataSourceName = PATH.resolveModelAttribute(context, model).asString();
    DataSourceNameService service = new DataSourceNameService(suffix, dataSourceName);
    ServiceName name = DataSourceNameService.createServiceName(suffix);
    context.getServiceTarget().addService(name, service).setInitialMode(Mode.ACTIVE).install();
  }
}
