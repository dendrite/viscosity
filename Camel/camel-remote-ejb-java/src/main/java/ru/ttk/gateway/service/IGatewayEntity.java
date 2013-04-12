package ru.ttk.gateway.service;

/**
 *
 */
public interface IGatewayEntity {

    public String getId();
    public String getPortableFullClassName();
    public String getDisplayName();
    public String getDataSourceName();

    // TODO check - is it possible to delete
    public void setId(String id);
    public void setPortableFullClassName(String fullClassName);
    public void setDisplayName(String displayName);
    public void setDataSourceName(String dataSourceName);

}
