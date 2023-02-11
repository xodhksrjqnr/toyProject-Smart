import React, { useContext, useRef, useState } from 'react';
import { categories } from '../constants/categories';
import { UserContext } from '../context/UserContext';
import OrderList from './OrderList';
import { useNavigate } from 'react-router-dom';

export default function ProductOrderForm({ productDetail }) {
  const [cart, setCart] = useState({ items: [] });
  const [isAddItem, setIsAddItem] = useState(false);
  const [isExiste, setExiste] = useState(false);
  const { productId, name, price, size, code, imgFiles } = productDetail;
  const [main, sub] = classification(code);
  const { user } = useContext(UserContext);
  const selectOption = useRef();
  const navigate = useNavigate();

  const handleOption = (e) => {
    const { value } = e.target;
    if (value === '') return;
    for (const registerdSize of cart.items) {
      if (registerdSize.size === value) return;
    }
    setCart((prev) => ({
      ...prev,
      items: [
        ...prev.items,
        {
          productId: productId,
          size: value,
          quantity: 1,
          img: imgFiles[0],
          price,
          name,
        },
      ],
    }));
  };

  const handleQuantity = (size, quantity) =>
    setCart((prev) => ({
      ...prev,
      items: prev.items.map((item) => {
        if (item.size === size) return { ...item, quantity: quantity };
        return { ...item };
      }),
    }));

  const handleDelete = (size) => {
    setCart((prev) => ({
      ...prev,
      items: prev.items.filter((item) => item.size !== size),
    }));
  };

  const handleSumbit = (e) => {
    e.preventDefault();

    if (!user.state) {
      navigate('/login');
      return;
    }
    if (cart.items.length === 0) return;
    if (
      !localStorage.getItem('cart') ||
      localStorage.getItem('cart') === 'null'
    ) {
      localStorage.setItem('cart', JSON.stringify(cart));
      setCart({ items: [] });
      setIsAddItem(true);
      setTimeout(() => setIsAddItem(false), 2000);
      return;
    }

    const savedCartItems = JSON.parse(localStorage.getItem('cart'));

    for (const savedItems of savedCartItems.items) {
      for (const cartItem of cart.items) {
        if (
          cartItem.productId === savedItems.productId &&
          cartItem.size === savedItems.size
        ) {
          setExiste(true);
          setTimeout(() => setExiste(false), 2000);
          return;
        }
      }
    }

    localStorage.setItem(
      'cart',
      JSON.stringify({
        ...cart,
        items: [...cart.items, ...savedCartItems.items],
      })
    );
    selectOption.current.value = '';
    setCart({ items: [] });
    setIsAddItem(true);
    setTimeout(() => setIsAddItem(false), 2000);
  };

  return (
    <section className="p-2 w-full lg:w-96 border-2">
      <h1 className="text-2xl font-bold mb-4">{name}</h1>
      <div className="mb-2 py-2 border-b-2 border-slate-200">
        <h2 className="font-bold">Product Info</h2>
        <p className="text-sm text-slate-800">
          {main} &gt; {sub}
        </p>
      </div>
      <div className="mb-12 py-2 border-b-2 border-slate-200">
        <h2 className="font-bold">Price Info</h2>
        <span>{price.toLocaleString()}원</span>
      </div>
      <div>
        <form onSubmit={handleSumbit}>
          <select
            name="size"
            id="size"
            className="w-full border-2 border-black"
            onChange={handleOption}
            ref={selectOption}
            value=""
          >
            <option value="">사이즈 선택</option>
            {size.split(',').map((value) => (
              <option key={value} value={value}>
                {value}
              </option>
            ))}
          </select>
          {cart.items.length >= 1 && (
            <OrderList
              cart={cart}
              price={price}
              onChangeQuantity={handleQuantity}
              onDelete={handleDelete}
            />
          )}
          <button className="bg-blue-400 mt-2 p-2 w-full font-bold rounded-md">
            장바구니 추가
          </button>
          {isExiste && (
            <p className="text-center mt-3 bg-red-300">
              해당 상품이 장바구니에 존재 합니다.
            </p>
          )}
          {isAddItem && (
            <p className="text-center mt-3 bg-green-300">
              장바구니에 추가 되었습니다.
            </p>
          )}
        </form>
      </div>
    </section>
  );
}

function classification(code) {
  let main;
  let sub;

  for (const category of categories) {
    if (category.code === code[0]) {
      main = category.main;

      for (const subdivision of category.subdivision) {
        if (subdivision.code === code) {
          sub = subdivision.title;
        }
      }
    }
  }
  return [main, sub];
}
