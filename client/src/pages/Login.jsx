import React, { useState } from 'react';
import { Link } from 'react-router-dom';

export default function Login() {
  const [privacy, setPrivacy] = useState({ id: '', password: '' });
  const handleChange = (e) => {
    const { name, value } = e.target;
    setPrivacy((prev) => ({ ...prev, [name]: value }));
  };
  const handleSubmit = (e) => {
    e.preventDefault();
  };
  return (
    <main className="w-screen h-screen bg-gray-200 flex justify-center items-center">
      <div className="w-96 h-96 bg-white flex flex-col justify-center items-center p-4 px-20 rounded-lg shadow-lg">
        <Link to="/" className="font-bold text-blue-800 text-4xl mb-2 ">
          SMART
        </Link>
        <form onSubmit={handleSubmit} className="flex flex-col mb-2">
          <input
            type="text"
            name="id"
            value={privacy.id}
            placeholder="아이디"
            onChange={handleChange}
            className="border mb-2 border-blue-800 p-1"
            required
          />
          <input
            type="password"
            name="password"
            value={privacy.password}
            placeholder="비밀번호"
            onChange={handleChange}
            className="border mb-2 border-blue-800 p-1"
            required
          />
          <button className="bg-blue-600 text-white p-1">로그인</button>
        </form>
        <div className="text-xs text-gray-400 flex justify-between w-full">
          <Link to="">회원가입</Link>
          <div>
            <Link to="" className="mr-2">
              ID찾기
            </Link>
            <Link to="">비밀번호찾기</Link>
          </div>
        </div>
      </div>
    </main>
  );
}
