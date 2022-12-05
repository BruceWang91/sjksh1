import {
  DEFAULT_AUTHORIZATION_TOKEN_EXPIRATION,
  StorageKeys,
} from 'globalConstants';
import Cookies from 'js-cookie';

let tokenExpiration = DEFAULT_AUTHORIZATION_TOKEN_EXPIRATION;

export function setTokenExpiration(expires: number) {
  tokenExpiration = expires;
}

export function getToken( bearer:boolean = true ) {
	const token = Cookies.get(StorageKeys.AuthorizationToken)
  return bearer ? token :  String(token).replace(/^Bearer\s{1}/,'');
}

export function setToken(token: string) {
  Cookies.set(StorageKeys.AuthorizationToken, token, {
    expires: new Date(new Date().getTime() + tokenExpiration),
  });
}

export function removeToken() {
  Cookies.remove(StorageKeys.AuthorizationToken);
}
