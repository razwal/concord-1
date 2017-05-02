package com.walmartlabs.concord.server.process.pipelines.processors;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.walmartlabs.concord.project.Constants;
import com.walmartlabs.concord.server.process.Payload;
import com.walmartlabs.concord.server.security.UserPrincipal;
import com.walmartlabs.concord.server.security.ldap.LdapInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Named;
import java.io.Serializable;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

@Named
public class UserInfoProcessor implements PayloadProcessor {

    private static final Logger log = LoggerFactory.getLogger(UserInfoProcessor.class);

    @Override
    public Payload process(Chain chain, Payload payload) {
        UserInfo info = getInfo();

        payload = payload.mergeValues(Payload.REQUEST_DATA_MAP,
                Collections.singletonMap(Constants.Request.INITIATOR_KEY, info));

        log.info("process ['{}'] -> done", payload.getInstanceId());
        return chain.process(payload);
    }

    private static UserInfo getInfo() {
        Subject subject = SecurityUtils.getSubject();
        if (subject == null || !subject.isAuthenticated()) {
            return null;
        }

        UserPrincipal u = (UserPrincipal) subject.getPrincipal();
        if (u == null) {
            return null;
        }

        UserInfo info;

        LdapInfo ldap = u.getLdapInfo();
        if (ldap != null) {
            info = new UserInfo(ldap.getUsername(), ldap.getDisplayName(), ldap.getGroups(), ldap.getAttributes());
        } else {
            info = new UserInfo(u.getUsername(), u.getUsername(), Collections.emptySet(), Collections.emptyMap());
        }

        return info;
    }

    @JsonInclude(Include.NON_NULL)
    public static class UserInfo implements Serializable {

        private final String username;
        private final String displayName;
        private final Set<String> groups;
        private final Map<String, String> attributes;

        public UserInfo(String username, String displayName, Set<String> groups, Map<String, String> attributes) {
            this.username = username;
            this.displayName = displayName;
            this.groups = groups;
            this.attributes = attributes;
        }

        public String getUsername() {
            return username;
        }

        public String getDisplayName() {
            return displayName;
        }

        public Set<String> getGroups() {
            return groups;
        }

        public Map<String, String> getAttributes() {
            return attributes;
        }
    }
}
