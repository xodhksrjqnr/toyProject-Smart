import { createContext, useEffect, useState } from 'react';
import { checkToken } from '../api/user';

export const UserContext = createContext();

export function UserProvider({ children }) {
  const [user, setUser] = useState({ state: false, id: '' });
  const setUserId = (id) => setUser((prev) => ({ ...prev, state: true, id }));
  const setLogout = () => setUser({ state: false, id: '' });

  useEffect(() => {
    console.log('실행');
    checkToken().then((res) => {
      if (!res) return;
      setUser((prev) => ({
        ...prev,
        state: true,
        id: res.data.memberId,
      }));
    });
  }, [user.state]);

  return (
    <UserContext.Provider value={{ user, setUserId, setLogout }}>
      {children}
    </UserContext.Provider>
  );
}
