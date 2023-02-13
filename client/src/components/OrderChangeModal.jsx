import React, { useState } from 'react';
import { orderChange } from '../api/orderChange';
import { useQueryClient } from '@tanstack/react-query';

export default function OrderChangeModal({
  title,
  orderItemId,
  closeModal,
  isCancel,
}) {
  const [text, setText] = useState('');
  const client = useQueryClient();
  const handleSumbit = (e) => {
    e.preventDefault();
    orderChange(orderItemId, text, isCancel).then(() => {
      client.invalidateQueries(['myorders']);
      closeModal();
    });
  };
  return (
    <div className="flex flex-col justify-center items-center fixed w-96 h-36 bg-gray-300 top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2 z-10 shadow-xl rounded-lg">
      <p>{title} 사유를 적어주세요.</p>
      <form onSubmit={handleSumbit}>
        <input
          className="border-2 border-black px-1"
          type="text"
          value={text}
          onChange={(e) => setText(e.target.value)}
          placeholder="사유"
          required
        ></input>
        <div className="flex justify-center mt-2">
          <button className="bg-white p-1 mr-8 rounded-lg" type="submit">
            확인
          </button>
          <button
            className="bg-white p-1 rounded-lg"
            type="button"
            onClick={closeModal}
          >
            취소
          </button>
        </div>
      </form>
    </div>
  );
}
