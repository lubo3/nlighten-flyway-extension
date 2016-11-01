package me.jboss.flyway.extension;

import org.jboss.msc.service.Service;
import org.jboss.msc.service.ServiceName;
import org.jboss.msc.service.StartContext;
import org.jboss.msc.service.StartException;
import org.jboss.msc.service.StopContext;

public class DataSourceNameService implements Service<DataSourceNameService> {

  private String dataSourceName;

  public DataSourceNameService(String suffix, String dataSourceName) {
    this.dataSourceName = dataSourceName;
  }

  @Override
  public DataSourceNameService getValue() throws IllegalStateException, IllegalArgumentException {
    return this;
  }

  @Override
  public void start(StartContext context) throws StartException {}

  @Override
  public void stop(StopContext context) {}

  public static ServiceName createServiceName(String suffix) {
    return ServiceName.JBOSS.append("flyway", suffix);
  }

  public String getDataSourceName() {
    return dataSourceName;
  }

  public void setDataSourceName(String dataSourceName) {
    this.dataSourceName = dataSourceName;
  }
}
