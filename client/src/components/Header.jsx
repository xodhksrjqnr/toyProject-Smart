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
    <header className="flex items-center border-b-2 border-gray-400 p-2">
      <Link to="/" className="mr-8">
        <h1 className="text-4xl">Smart</h1>
      </Link>
      <form
        className="flex justify-between border-2 border-gray-300 rounded-full px-3 py-1 w-72"
        onSubmit={handleSumbit}
      >
        <input
          className="text-sm w-full "
          type="text"
          placeholder="상품 검색"
          value={text}
          onChange={(e) => setText(e.target.value)}
        />
        <button>
          <FiSearch />
        </button>
      </form>
    </header>
  );
}
