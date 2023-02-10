import React from 'react';

export default function MyOrderItem({ order }) {
  return (
    <li className="mb-2 border-2 border-gray-400 rounded-lg p-4">
      <strong className="bg-blue-400 w-12 block rounded-md text-center mb-2">
        {order.orderId}
      </strong>
      {order.orderItemInfoDtoList.map((item) => (
        <section
          className="pl-4 border-b-2 border-dashed border-gray-400 last:border-none py-2"
          key={item.productId}
        >
          <div className="flex">
            <img
              className="w-24 h-24 object-cover border-2 border-black mr-2"
              src={item.thumbnail}
              alt={item.name}
            ></img>
            <div>
              <strong>{item.name}</strong>
              <p>가격 : {item.price}</p>
              <p>수량 : {item.quantity}</p>
              <p>사이즈 : {item.size}</p>
            </div>
          </div>
        </section>
      ))}
    </li>
  );
}
