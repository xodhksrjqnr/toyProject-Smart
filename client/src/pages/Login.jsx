import React, { useContext, useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { login } from '../api/user';
import { UserContext } from '../context/UserContext';
import { emailCertification } from '../api/emailCertification';
import OnlyEmailCertificateModal from '../components/OnlyEmailCertificateModal';
import FindPasswordModal from '../components/FindPasswordModal';

export default function Login() {
  const [privacy, setPrivacy] = useState({ id: '', password: '' });
  const [isSuccess, setisSuccess] = useState(true);
  const [emailModal, setEmailModal] = useState(false);
  const [findIdModal, setfindIdModal] = useState(false);
  const [findPasswordModal, setFindPasswordModal] = useState(false);
  const { setUserId } = useContext(UserContext);
  const navigate = useNavigate();
  const handleChange = (e) => {
    const { name, value } = e.target;
    setPrivacy((prev) => ({ ...prev, [name]: value }));
  };
  const openEmailModal = () => setEmailModal(true);
  const openfindIdModal = () => setfindIdModal(true);
  const openFindPasswordModal = () => setFindPasswordModal(true);
  const handleSubmit = (e) => {
    e.preventDefault();
    login(privacy) //
      .then((res) => {
        if (res.status === 200) {
          setUserId(res.data.memberId);
          navigate(-1);
        } else {
          setisSuccess(false);
          setTimeout(() => setisSuccess(true), 3000);
        }
      });
  };
  return (
    <main className="w-screen h-screen bg-gray-200 flex justify-center items-center">
      <div className="w-96 h-96 bg-white flex flex-col justify-center items-center p-4 px-20 rounded-lg shadow-lg">
        <Link to="/" className="font-bold text-blue-800 text-4xl mb-2 ">
          SMART
        </Link>
        <form className="flex flex-col mb-2" onSubmit={handleSubmit}>
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
          <button type="button" onClick={openEmailModal}>
            회원가입
          </button>

          <div>
            <button type="button" onClick={openfindIdModal} className="mr-2">
              ID찾기
            </button>
            <button type="button" onClick={openFindPasswordModal}>
              비밀번호찾기
            </button>
          </div>
        </div>
        {!isSuccess && (
          <p className="text-xs text-red-600 mt-4">
            아이디 또는 비밀번호가 일치하지 않습니다.
          </p>
        )}

        {emailModal && (
          <OnlyEmailCertificateModal
            closeModal={setEmailModal}
            onEmailCertication={emailCertification}
          />
        )}
        {findIdModal && (
          <OnlyEmailCertificateModal
            closeModal={setfindIdModal}
            onEmailCertication={emailCertification}
            findId={true}
          />
        )}
        {findPasswordModal && (
          <FindPasswordModal closeModal={setFindPasswordModal} />
        )}
      </div>
    </main>
  );
}
