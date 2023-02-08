import React, { useState } from 'react';
import { findPassword } from '../api/findPassword';

export default function FindPasswordModal({ closeModal }) {
  const [text, setText] = useState({});
  const [validEmail, setValidEmail] = useState(false);
  const handleChange = (e) => {
    const { name, value } = e.target;
    setText((prev) => ({ ...prev, [name]: value }));
  };
  const handleSumbit = (e) => {
    e.preventDefault();
    if (!text.email.includes('@')) return setValidEmail(false);
    findPassword(text).then((res) => {
      if (res.status === 200) setValidEmail(true);
    });
  };
  return (
    <div className="fixed w-96 h-48 bg-blue-300 top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2 z-10 shadow-xl rounded-lg text-center border-2 border-blue-800">
      <form
        onSubmit={handleSumbit}
        className="flex justify-center text-black pt-8"
      >
        <div className="flex flex-col justify-center items-center mb-3">
          <div className="mb-2">
            <label htmlFor="email">이메일</label>
            <input
              id="email"
              type="email"
              name="email"
              value={text.email ?? ''}
              placeholder="이메일"
              onChange={handleChange}
              className="border border-blue-800 p-1 mx-2"
              required
            />
          </div>

          <div>
            <label htmlFor="id">아이디</label>
            <input
              id="id"
              type="text"
              name="id"
              value={text.id ?? ''}
              placeholder="아이디"
              onChange={handleChange}
              className="border border-blue-800 p-1 mx-2"
              required
            />
          </div>
        </div>
        <div>
          <button className="bg-white p-1 px-3 rounded-md mr-2" type="submit">
            인증
          </button>
        </div>
      </form>
      <div className="flex justify-center items-center text-md mb-1">
        {validEmail && <p>이메일을 확인해 주세요.</p>}
      </div>
      <button
        className="p-1 bg-white w-14 mx-auto my-0 rounded-xl"
        type="button"
        onClick={() => closeModal(false)}
      >
        닫기
      </button>
    </div>
  );
}
