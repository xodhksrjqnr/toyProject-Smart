import { createContext, useEffect, useState } from 'react';
import { checkToken } from '../api/user';

export const UserContext = createContext();

export function UserProvider({ children }) {
  const [user, setUser] = useState({ state: false, id: '' });
  const setUserId = (id) => setUser((prev) => ({ ...prev, state: true, id }));
  const setLogout = () => setUser({ state: false, id: '' });

  useEffect(() => {
    const id = checkToken();

    if (id) {
      setUser((prev) => ({ ...prev, state: true, id: id }));
    }
  }, [user.id]);

  return (
    <UserContext.Provider value={{ user, setUserId, setLogout }}>
      {children}
    </UserContext.Provider>
  );
}
