import React from 'react';
import { logout } from '../../api/user';

export default function LogoutButton({ onClickLogout }) {
  const handleLogout = () => {
    logout().then(() => onClickLogout());
  };
  return <button onClick={handleLogout}>로그아웃</button>;
}
