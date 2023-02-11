import React from 'react';
import OrderChangeButton from './OrderChangeButton';

export default function OrderState({ orderItemId, state }) {
  return (
    <div className="ml-2 flex flex-col">
      <span className="bg-green-400 rounded-2xl px-2 mb-2">상태 : {state}</span>
      {state === '대기중' && (
        <OrderChangeButton
          state={state}
          orderItemId={orderItemId}
          isCancel={true}
        />
      )}
      {state === '배송완료' && (
        <OrderChangeButton state={state} orderItemId={orderItemId} />
      )}
    </div>
  );
}
