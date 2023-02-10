import React from 'react';
import { useQuery } from '@tanstack/react-query';
import Loading from '../components/ui/Loading';
import { myorder } from '../api/orders';
import MyOrderItem from '../components/MyOrderItem';

export default function Myorder() {
  const { isLoading, data: orderList } = useQuery(['myorders'], () =>
    myorder()
  );
  if (isLoading) return <Loading />;
  console.log(orderList);

  return (
    <div className="w-full p-8">
      <h2 className="text-2xl font-bold mb-2">주문목록</h2>
      <ul>
        {orderList &&
          orderList.data.map((order) => (
            <MyOrderItem key={order.orderId} order={order} />
          ))}
      </ul>
    </div>
  );
}
