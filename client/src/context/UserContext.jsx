import { createContext, useEffect, useState } from 'react';
import { checkToken } from '../api/user';
import Cookies from 'universal-cookie';

export const UserContext = createContext();

export function UserProvider({ children }) {
  const [user, setUser] = useState({ state: false, id: '' });
  const setUserId = (id) => setUser((prev) => ({ ...prev, state: true, id }));
  const setLogout = () => setUser({ state: false, id: '' });
  const cookies = new Cookies();

  useEffect(() => {
    checkToken().then((res) => {
      if (!res) return;
      if (res.data.code === 404) {
        cookies.remove('id');
        cookies.remove('loginToken');
        cookies.remove('refreshToken');
        return;
      }
      setUser((prev) => ({
        ...prev,
        state: true,
        id: res.data.nickName,
      }));
    });
  }, []);

  return (
    <UserContext.Provider value={{ user, setUserId, setLogout }}>
      {children}
    </UserContext.Provider>
  );
}
