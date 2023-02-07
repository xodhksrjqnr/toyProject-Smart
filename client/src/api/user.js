import axios from 'axios';
import Cookies from 'universal-cookie';

export async function login(privacy) {
  const { id, password } = privacy;
  const data = new FormData();

  data.append('memberId', id);
  data.append('password', password);

  return axios
    .post(process.env.REACT_APP_LOGIN_URL, data)
    .then((res) => {
      setToken(res.data.memberId, res.data.loginToken, res.data.refreshToken);
      return res;
    })
    .catch((error) => {
      console.error(error);
      return error.response;
    });
}

export async function logout() {
  const cookies = new Cookies();
  const data = new FormData();

  return axios
    .post(process.env.REACT_APP_LOGOUT_URL, data, {
      headers: {
        Authorization: `loginToken=${cookies.get(
          'loginToken'
        )}; refreshToken=${cookies.get('refreshToken')}`,
      },
      withCredentials: true,
    })
    .then(() => {
      cookies.remove('id');
      cookies.remove('loginToken');
      cookies.remove('refreshToken');
    })
    .catch((error) => error.response);
}

export function setToken(id, loginToken, refreshToken) {
  const cookies = new Cookies();
  cookies.set('id', id);
  cookies.set('loginToken', loginToken);
  cookies.set('refreshToken', refreshToken);
}

export function checkToken() {
  const cookies = new Cookies();
  if (cookies.get('loginToken') && cookies.get('refreshToken'))
    return cookies.get('id');
}
