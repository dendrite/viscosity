package ru.ttk.building.data.model;

import java.io.Serializable;
import java.util.UUID;

/**
 *
 */
public abstract class BObject implements Serializable {

    private static final long serialVersionUID = 1L;

    protected String id;
    private long version;
    private String displayName;
    private boolean deleted;

    public BObject() {
        id = UUID.randomUUID().toString();
        deleted = false;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @SuppressWarnings("unused")
    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    @SuppressWarnings("unused")
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
