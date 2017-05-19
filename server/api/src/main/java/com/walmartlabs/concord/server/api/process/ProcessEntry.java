package com.walmartlabs.concord.server.api.process;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;

@JsonInclude(Include.NON_NULL)
public class ProcessEntry implements Serializable {

    private final String instanceId;
    private final String projectName;
    private final Date createdAt;
    private final String initiator;
    private final ProcessStatus status;
    private final String lastAgentId;
    private final Date lastUpdatedAt;
    private final String logFileName;

    @JsonCreator
    public ProcessEntry(@JsonProperty("instanceId") String instanceId,
                        @JsonProperty("projectName") String projectName,
                        @JsonProperty("createdAt") Date createdAt,
                        @JsonProperty("initiator") String initiator,
                        @JsonProperty("lastUpdatedAt") Date lastUpdatedAt,
                        @JsonProperty("status") ProcessStatus status,
                        @JsonProperty("lastAgentId") String lastAgentId) {

        this.instanceId = instanceId;
        this.projectName = projectName;
        this.createdAt = createdAt;
        this.initiator = initiator;
        this.lastUpdatedAt = lastUpdatedAt;
        this.status = status;
        this.lastAgentId = lastAgentId;

        // TODO left for backwards compatibility
        this.logFileName = instanceId + ".log";
    }

    public String getInstanceId() {
        return instanceId;
    }

    public String getProjectName() {
        return projectName;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public String getInitiator() {
        return initiator;
    }

    public ProcessStatus getStatus() {
        return status;
    }

    public String getLastAgentId() {
        return lastAgentId;
    }

    public Date getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public String getLogFileName() {
        return logFileName;
    }

    @Override
    public String toString() {
        return "ProcessEntry{" +
                "instanceId='" + instanceId + '\'' +
                ", projectName='" + projectName + '\'' +
                ", createdAt=" + createdAt +
                ", initiator='" + initiator + '\'' +
                ", status=" + status +
                ", lastAgentId='" + lastAgentId + '\'' +
                ", lastUpdatedAt=" + lastUpdatedAt +
                '}';
    }
}