import axios from 'axios';
import Cookies from 'universal-cookie';

export async function login(privacy) {
  const { id, password } = privacy;
  const data = new FormData();

  data.append('nickName', id);
  data.append('password', password);

  return axios
    .post(process.env.REACT_APP_LOGIN_URL, data)
    .then((res) => {
      setToken(res.data.nickName, res.data.loginToken, res.data.refreshToken);
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
        Authorization: `Bearer ${cookies.get('loginToken')}`,
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
  cookies.set('id', id, { maxAge: 60 * 30 });
  cookies.set('loginToken', loginToken, { maxAge: 60 * 30 });
  cookies.set('refreshToken', refreshToken, { maxAge: 60 * 120 });
}

export async function checkToken() {
  const cookies = new Cookies();
  const data = new FormData();
  const loginToken = cookies.get('loginToken');
  const refreshToken = cookies.get('refreshToken');

  if (!loginToken && !refreshToken) return;

  console.log('토큰이 있네');
  return axios
    .post(process.env.REACT_APP_TOKEN_URL, data, {
      headers: {
        Authorization: `Bearer ${refreshToken}`,
      },
      withCredentials: true,
    })
    .then((res) => {
      cookies.set('id', res.data.nickName, { maxAge: 60 * 30 });
      cookies.set('loginToken', res.data.loginToken, {
        maxAge: 60 * 30,
      });
      cookies.set('refreshToken', res.data.refreshToken, {
        maxAge: 60 * 120,
      });
      return res;
    })
    .catch((error) => error.response);
}
