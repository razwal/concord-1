import * as common from "../../api";

export const createNewKeyPair = ( { name, generatePassword = false, storePassword = null } ) => {

    // Todo: Verify that query params are not added if they are null or undefined
    const query = common.queryParams({
        name,
        generatePassword
    });

    let opts = { method: "POST", credentials: "same-origin" }

    if( storePassword ) {
        const MultipartFormData = new FormData();
        MultipartFormData.append('storePassword', storePassword);

        opts.body = MultipartFormData;
    }

    return fetch(`/api/v1/secret/keypair?${query}`, opts)
        .then(response => {
            if (!response.ok) {
                throw common.defaultError(response);
            }
            return response.json();
        });
};

export const createPlainSecret = ( { name, generatePassword = false, secret } ) => {

    const query = common.queryParams({
        name,
        generatePassword
    });

    const MultipartFormData = new FormData();
    MultipartFormData.append('secret', secret);

    return fetch(`/api/v1/secret/plain?${query}`, {method: "POST", credentials: "same-origin", body: MultipartFormData })
        .then( response => {
            if (!response.ok) {
                throw common.defaultError(response);
            }
            return response.json();
        });
}

export const uploadExistingKeyPair = ( { name, generatePassword = false, publicKey, privateKey, storePassword } ) => {
    
    console.log( "API! UPLOAD ExistingKeyPair: " + publicKey );

    const query = common.queryParams({
        name,
        generatePassword
    });

    const MultipartFormData = new FormData();
    MultipartFormData.append('public', publicKey);
    MultipartFormData.append('private', privateKey);

    if ( storePassword )
        MultipartFormData.append('storePassword', storePassword);

    return fetch(`/api/v1/secret/keypair?${query}`, {method: "POST", credentials: "same-origin", body: MultipartFormData })
        .then( response => {
            if (!response.ok) {
                throw common.defaultError(response);
            }
            return response.json();
        });
}

export const createUserCredentials = ( { name, generatePassword = false, username, password, storePassword } ) => {

    const query = common.queryParams({
        name,
        generatePassword
    });

    const MultipartFormData = new FormData();
    MultipartFormData.append('username', username);
    MultipartFormData.append('password', password);

    if ( storePassword )
        MultipartFormData.append('storePassword', storePassword);

    return fetch(`/api/v1/secret/password?${query}`, {method: "POST", credentials: "same-origin", body: MultipartFormData })
        .then( response => {
            if (!response.ok) {
                throw common.defaultError(response);
            }
            return response.json();
        });
}