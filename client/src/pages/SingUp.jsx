import React, { useState } from 'react';
import { signup } from '../api/signup';

export default function SingUp() {
  const [info, setInfo] = useState({
    id: '',
    password: '',
    checkPassword: '',
    email: '',
    birthday: '',
    phoneNumber: '',
  });
  const [password, setPassword] = useState({ length: false, available: false });
  const handleChange = (e) => {
    const { name, value } = e.target;
    setInfo((prev) => ({ ...prev, [name]: value }));
  };
  const handleSubmit = (e) => {
    e.preventDefault();
    signup(info).then((data) => console.log(data));
  };
  const checkPasswordLength = (e) => {
    e.target.value.length >= 1
      ? setPassword((prev) => ({ ...prev, length: true }))
      : setPassword((prev) => ({ ...prev, length: false }));
  };
  const checkPasswordEqual = (e) => {
    info.password === e.target.value || info.checkPassword === e.target.value
      ? setPassword((prev) => ({ ...prev, available: true }))
      : setPassword((prev) => ({ ...prev, available: false }));
  };
  return (
    <div className="w-full flex justify-center p-12">
      <form onSubmit={handleSubmit} className="w-96 flex flex-col mb-2">
        <label htmlFor="id">
          <span className="text-red-600">*</span>아이디
        </label>
        <input
          id="id"
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
          id="password"
          type="password"
          name="password"
          value={info.password}
          placeholder="비밀번호"
          onChange={(e) => {
            handleChange(e);
            checkPasswordLength(e);
            checkPasswordEqual(e);
          }}
          className="border mb-2 border-blue-800 p-1"
          required
        />

        <label htmlFor="checkPassword">
          <span className="text-red-600">*</span>비밀번호 확인
        </label>
        <input
          id="checkPassword"
          type="password"
          name="checkPassword"
          value={info.checkPassword}
          placeholder="비밀번호 확인"
          onChange={(e) => {
            handleChange(e);
            checkPasswordEqual(e);
          }}
          className="border mb-2 border-blue-800 p-1"
          required
        />
        {password.available && info.checkPassword.length >= 1 && (
          <p className="mb-2 text-green-600">비밀번호가 일치합니다.</p>
        )}
        {!password.available && info.checkPassword.length >= 1 && (
          <p className="mb-2 text-red-600">비밀번호가 일치하지 않습니다.</p>
        )}

        <label htmlFor="email">
          <span className="text-red-600">*</span>이메일
        </label>
        <input
          id="email"
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
          id="birthday"
          type="date"
          name="birthday"
          value={info.birthday}
          placeholder="비밀번호"
          onChange={handleChange}
          className="border mb-2 border-blue-800 p-1"
        />

        <label htmlFor="phoneNumber">휴대전화</label>
        <input
          id="phoneNumber"
          type="tel"
          name="phoneNumber"
          value={info.phone}
          placeholder="휴대전화"
          onChange={handleChange}
          className="border mb-2 border-blue-800 p-1"
        />
        <button
          className={`${
            !password.available ? 'bg-red-600' : 'bg-blue-600'
          }  text-white p-1`}
          disabled={!password.available}
        >
          회원가입
        </button>
      </form>
    </div>
  );
}
