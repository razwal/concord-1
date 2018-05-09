/*-
 * *****
 * Concord
 * -----
 * Copyright (C) 2017 - 2018 Wal-Mart Store, Inc.
 * -----
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * =====
 */
import * as React from 'react';
import { Dropdown, Icon } from 'semantic-ui-react';

import { ConcordKey } from '../../../api/common';
import {
    DeleteRepositoryPopup,
    RefreshRepositoryPopup,
    StartRepositoryPopup
} from '../../organisms';

interface ExternalProps {
    orgName: ConcordKey;
    projectName: ConcordKey;
    repoName: ConcordKey;
}

class RepositoryActionDropdown extends React.PureComponent<ExternalProps> {
    render() {
        const { orgName, projectName, repoName } = this.props;

        return (
            <Dropdown icon="ellipsis vertical">
                <Dropdown.Menu>
                    <StartRepositoryPopup
                        orgName={orgName}
                        projectName={projectName}
                        repoName={repoName}
                        trigger={(onClick) => (
                            <Dropdown.Item onClick={onClick}>
                                <Icon name="play" color="blue" />
                                <span className="text">Run</span>
                            </Dropdown.Item>
                        )}
                    />

                    <RefreshRepositoryPopup
                        orgName={orgName}
                        projectName={projectName}
                        repoName={repoName}
                        trigger={(onClick) => (
                            <Dropdown.Item onClick={onClick}>
                                <Icon name="refresh" />
                                <span className="text">Refresh</span>
                            </Dropdown.Item>
                        )}
                    />

                    <DeleteRepositoryPopup
                        orgName={orgName}
                        projectName={projectName}
                        repoName={repoName}
                        trigger={(onClick) => (
                            <Dropdown.Item onClick={onClick}>
                                <Icon name="delete" color="red" />
                                <span className="text">Delete</span>
                            </Dropdown.Item>
                        )}
                    />
                </Dropdown.Menu>
            </Dropdown>
        );
    }
}

export default RepositoryActionDropdown;
