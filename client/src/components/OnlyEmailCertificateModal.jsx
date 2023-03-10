import React, { useState } from 'react';
import Loading from './ui/Loading';
import emailRegexCheck from '../util/emailRegexCheck';

export default function OnlyEmailCertificateModal({
  closeModal,
  onEmailCertication,
  findId,
}) {
  const [text, setText] = useState('');
  const [isLoading, setIsLoading] = useState(false);
  const [validEmail, setvalidEmail] = useState(false);
  const handleEmailcertification = () => {
    if (!text.includes('@')) return setvalidEmail(false);
    setIsLoading(true);
    onEmailCertication(text, findId)
      .then((res) => {
        if (res.status === 200) setvalidEmail(true);
      })
      .finally(() => setIsLoading(false));
  };
  const handleChange = (e) => setText(e.target.value);
  return (
    <div className="fixed w-96 h-36 bg-blue-300 top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2 z-10 shadow-xl rounded-lg border-2 border-blue-800">
      <form className="flex flex-col text-black pt-8">
        <div className="flex justify-center items-center mb-2">
          <label htmlFor="email">이메일</label>
          <input
            id="email"
            type="email"
            name="email"
            value={text}
            placeholder="이메일"
            onChange={handleChange}
            className="border border-blue-800 p-1 mx-2"
            required
          />
          <button
            className={`${
              !emailRegexCheck(text) && 'cursor-not-allowed'
            } bg-white p-1 px-3 rounded-md mr-2`}
            type="button"
            onClick={handleEmailcertification}
            disabled={!emailRegexCheck(text)}
          >
            인증
          </button>
        </div>
        <div className="flex justify-center items-center text-md mb-1">
          {isLoading && <Loading />}
          {validEmail && text.length >= 1 && <p>이메일을 확인해 주세요.</p>}
        </div>
        <button
          className="p-1 bg-white w-14 mx-auto my-0 rounded-xl"
          type="button"
          onClick={() => closeModal(false)}
        >
          닫기
        </button>
      </form>
    </div>
  );
}
