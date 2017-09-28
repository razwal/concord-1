package com.walmartlabs.concord.server.security.secret;

import com.walmartlabs.concord.common.secret.SecretStoreType;
import com.walmartlabs.concord.server.AbstractDaoTest;
import com.walmartlabs.concord.server.api.project.RepositoryEntry;
import com.walmartlabs.concord.server.api.security.secret.SecretType;
import com.walmartlabs.concord.server.project.ProjectDao;
import com.walmartlabs.concord.server.project.RepositoryDao;
import com.walmartlabs.concord.server.security.secret.SecretDao;
import com.walmartlabs.concord.server.user.UserPermissionCleaner;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;

@Ignore("requires a local DB instance")
public class SecretDaoTest extends AbstractDaoTest {

    @Test
    public void testOnCascade() {
        String projectName = "project#" + System.currentTimeMillis();

        ProjectDao projectDao = new ProjectDao(getConfiguration(), mock(UserPermissionCleaner.class));
        projectDao.insert(projectName, "test", null);

        String secretName = "secret#" + System.currentTimeMillis();
        SecretDao secretDao = new SecretDao(getConfiguration(), mock(UserPermissionCleaner.class));
        secretDao.insert(secretName, SecretType.KEY_PAIR, SecretStoreType.SERVER_KEY, new byte[]{0, 1, 2});

        String repoName = "repo#" + System.currentTimeMillis();
        RepositoryDao repositoryDao = new RepositoryDao(getConfiguration());
        repositoryDao.insert(projectName, repoName, "n/a", null, null, null, secretName);

        // ---

        secretDao.delete(secretName);

        // ---

        RepositoryEntry r = repositoryDao.get(projectName, repoName);
        assertNotNull(r);
        assertNull(r.getSecret());
    }
}