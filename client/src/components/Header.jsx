import React, { useState } from 'react';
import { FiSearch } from 'react-icons/fi';
import { Link, useNavigate } from 'react-router-dom';

export default function Header() {
  const [text, setText] = useState('');
  const navigate = useNavigate();
  const handleSumbit = (e) => {
    e.preventDefault();
    if (text.trim().length === 0) return;
    navigate(`/categories/${text}`);
  };
  return (
    <header className="flex justify-center bg-violet-400">
      <div className="max-w-screen-2xl w-full flex items-center px-2 py-1">
        <Link to="/" className="mr-8 relative -top-1">
          <h1 className="text-3xl text-slate-900">Smart</h1>
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
    </header>
  );
}
