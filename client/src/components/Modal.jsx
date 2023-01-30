import React from 'react';
import { remove } from '../api/remove';
import { useQueryClient } from '@tanstack/react-query';

export default function Modal({ product, toggleModal }) {
  const client = useQueryClient();
  const handleDelete = () => {
    remove(product.id) //
      .then(() => client.invalidateQueries(['products']));
    toggleModal();
  };
  const handleClose = () => toggleModal();

  return (
    <div className="flex flex-col justify-center items-center fixed w-96 h-36 bg-rose-200 top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2 z-10 shadow-xl rounded-lg">
      <p className="mb-4">
        <strong>{product.name}</strong> 상품을 삭제하겠습니까?
      </p>
      <div>
        <button
          className="bg-white rounded-md p-1 mr-2 hover:bg-blue-200"
          onClick={handleDelete}
        >
          삭제
        </button>
        <button
          className="bg-white rounded-md p-1 hover:bg-blue-200"
          onClick={handleClose}
        >
          취소
        </button>
      </div>
    </div>
  );
}
