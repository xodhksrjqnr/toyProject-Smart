import React, { useContext, useState } from 'react';
import { FiSearch } from 'react-icons/fi';
import { Link, useNavigate } from 'react-router-dom';
import { UserContext } from '../context/UserContext';
import CartButton from './ui/CartButton';
import AdminButton from './ui/AdminButton';
import Avatar from './Avatar';
import LoginButton from './ui/LoginButton';
import LogoutButton from './ui/LogoutButton';

export default function Header() {
  const [text, setText] = useState('');
  const { user, setLogout } = useContext(UserContext);
  const navigate = useNavigate();
  const handleSumbit = (e) => {
    e.preventDefault();
    if (text.trim().length === 0) return;
    navigate({ pathname: '/search', search: `?keyword=${text}` });
  };
  return (
    <header className="w-full flex justify-center bg-blue-800 drop-shadow-md">
      <div className="max-w-screen-2xl w-full flex justify-between items-center px-2 py-1">
        <div className="flex items-center">
          <Link to="/" className="mr-8 relative -top-1">
            <h1 className="text-3xl text-white">Smart</h1>
          </Link>
          <form
            className="flex justify-between rounded-full bg-white overflow-hidden h-7 px-2"
            onSubmit={handleSumbit}
          >
            <input
              className="text-sm w-full "
              type="text"
              placeholder="Search"
              value={text}
              onChange={(e) => setText(e.target.value)}
            />
            <button>
              <FiSearch />
            </button>
          </form>
        </div>
        <div className="flex justify-around text-white w-40">
          <CartButton />
          <AdminButton />
          {user.state && <Avatar user={user} />}
          {user.state && <LogoutButton onClickLogout={setLogout} />}
          {!user.state && <LoginButton />}
        </div>
      </div>
    </header>
  );
}
