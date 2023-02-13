import React, { useState } from 'react';
import OrderChangeModal from './OrderChangeModal';

export default function OrderChangeButton({ state, orderItemId, isCancel }) {
  const [stateModal, setStateModal] = useState(false);
  const closeModal = () => setStateModal(false);
  const text = isCancel ? '취소' : '환불';

  return (
    <div className="relative flex flex-col mb-2 items-end">
      <button
        className={`${
          state === '상품 준비중' ? 'hidden' : 'bg-red-300'
        } rounded-lg px-2 w-24`}
        type="button"
        onClick={() => setStateModal((prev) => !prev)}
      >
        {text}
      </button>
      {stateModal && (
        <OrderChangeModal
          title={text}
          orderItemId={orderItemId}
          closeModal={closeModal}
          isCancel={isCancel}
        />
      )}
    </div>
  );
}
