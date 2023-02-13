import React from 'react';
import OrderState from './OrderState';
import { useNavigate } from 'react-router-dom';

export default function MyOrderItem({ order }) {
  const navigate = useNavigate();
  const handleClick = (productId) => navigate(`/products/${productId}`);
  return (
    <li className="mb-2 border-2 border-gray-400 rounded-lg p-4 max-w-4xl">
      <strong className="bg-blue-400 w-12 inline-block rounded-md text-center mb-2 mr-4">
        {order.orderId}
      </strong>

      {order.orderItemInfoDtoList.map((item) => (
        <section
          className="pl-4 border-b-2 border-dashed border-gray-400 last:border-none py-2"
          key={item.productId}
        >
          <div className="flex justify-between">
            <div className="flex">
              <img
                className="w-24 h-24 object-cover border-2 border-black mr-2 cursor-pointer"
                src={item.thumbnail}
                alt={item.name}
                onClick={() => handleClick(item.productId)}
              ></img>
              <div>
                <strong
                  className="cursor-pointer"
                  onClick={() => handleClick(item.productId)}
                >
                  {item.name}
                </strong>
                <p>가격 : {item.price}</p>
                <p>수량 : {item.quantity}</p>
                <p>사이즈 : {item.size}</p>
              </div>
            </div>
            <OrderState
              orderItemId={item.orderItemId}
              state={item.deliveryStatus}
            />
          </div>
        </section>
      ))}
    </li>
  );
}
