import React, { useContext, useState } from 'react';
import { FiSearch } from 'react-icons/fi';
import { MdAdminPanelSettings } from 'react-icons/md';
import { Link, useNavigate } from 'react-router-dom';
import { BsCartPlusFill } from 'react-icons/bs';
import { UserContext } from '../context/UserContext';
import { logout } from '../api/user';

export default function Header() {
  const [text, setText] = useState('');
  const { user, setLogout } = useContext(UserContext);
  const navigate = useNavigate();
  const handleSumbit = (e) => {
    e.preventDefault();
    if (text.trim().length === 0) return;
    navigate({ pathname: '/search', search: `?keyword=${text}` });
  };
  const handleLogout = () => {
    logout().then(() => setLogout());
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
        <div className="flex text-white">
          <button>
            <Link to="/carts">
              <BsCartPlusFill className="text-white text-2xl" />
            </Link>
          </button>
          <button>
            <Link to="/admin">
              <MdAdminPanelSettings className="text-white text-2xl" />
            </Link>
          </button>
          {user.state && <p>{user.id}님</p>}
          <button>
            {user.state && <p onClick={handleLogout}>로그아웃</p>}
            {!user.state && <Link to="/login">로그인</Link>}
          </button>
        </div>
      </div>
    </header>
  );
}
