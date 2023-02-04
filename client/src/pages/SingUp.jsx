import React, { useState } from 'react';
import { signup } from '../api/signup';

export default function SingUp() {
  const [info, setInfo] = useState({
    id: '',
    password: '',
    checkPassword: '',
    email: '',
    birthday: '',
    phone: '',
  });
  const handleChange = (e) => {
    const { name, value } = e.target;
    setInfo((prev) => ({ ...prev, [name]: value }));
  };
  const handleSubmit = (e) => {
    e.preventDefault();
    signup(info).then((data) => console.log(data));
  };
  return (
    <div className="w-full flex justify-center p-12">
      <form onSubmit={handleSubmit} className="w-96 flex flex-col mb-2">
        <label htmlFor="id">
          <span className="text-red-600">*</span>아이디
        </label>
        <input
          type="text"
          name="id"
          value={info.id}
          placeholder="아이디"
          onChange={handleChange}
          className="border mb-2 border-blue-800 p-1"
          required
        />
        <label htmlFor="password">
          <span className="text-red-600">*</span>비밀번호
        </label>
        <input
          type="password"
          name="password"
          value={info.password}
          placeholder="비밀번호"
          onChange={handleChange}
          className="border mb-2 border-blue-800 p-1"
          required
        />
        <label htmlFor="checkPassword">
          <span className="text-red-600">*</span>비밀번호 재확인
        </label>
        <input
          type="password"
          name="checkPassword"
          value={info.checkPassword}
          placeholder="비밀번호 확인"
          onChange={handleChange}
          className="border mb-2 border-blue-800 p-1"
          required
        />
        <label htmlFor="email">
          <span className="text-red-600">*</span>이메일
        </label>
        <input
          type="email"
          name="email"
          value={info.email}
          placeholder="이메일"
          onChange={handleChange}
          className="border mb-2 border-blue-800 p-1"
          required
        />
        <label htmlFor="birthday">생년월일</label>
        <input
          type="date"
          name="birthday"
          value={info.birthday}
          placeholder="비밀번호"
          onChange={handleChange}
          className="border mb-2 border-blue-800 p-1"
        />
        <label htmlFor="phone">휴대전화</label>
        <input
          type="tel"
          name="phone"
          value={info.phone}
          placeholder="휴대전화"
          onChange={handleChange}
          className="border mb-2 border-blue-800 p-1"
        />
        <button className="bg-blue-600 text-white p-1">회원가입</button>
      </form>
    </div>
  );
}
