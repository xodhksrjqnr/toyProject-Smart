import React, { useState } from 'react';
import { upload } from '../api/uploader';

export default function Register() {
  const [product, setProduct] = useState({});
  const [file, setFile] = useState([]);
  const [detail, setDetail] = useState();
  const handleChange = (e) => {
    const { name, value, files } = e.target;
    if (name === 'imgFiles') {
      for (const value of files) {
        setFile((prev) => [...prev, value]);
      }
      return;
    }
    if (name === 'detailInfo') {
      setDetail(files && files[0]);
      return;
    }
    setProduct((product) => ({ ...product, [name]: value }));
  };
  const handleSubmit = (e) => {
    e.preventDefault();
    upload(product, file, detail);
  };
  return (
    <section>
      <form method="POST" encType="multipart/form-data" onSubmit={handleSubmit}>
        <input
          type="file"
          accept="image/*"
          name="imgFiles"
          multiple="multiple"
          required
          onChange={handleChange}
        />
        <input
          type="file"
          accept="image/*"
          name="detailInfo"
          required
          onChange={handleChange}
        />
        <input
          type="text"
          name="name"
          value={product.name ?? ''}
          placeholder="제품명"
          required
          onChange={handleChange}
        />
        <input
          type="text"
          name="price"
          value={product.price ?? ''}
          placeholder="가격"
          required
          onChange={handleChange}
        />
        <select name="code" onChange={handleChange}>
          <option value="A01" selected>
            후드 티셔츠
          </option>
          <option value="A02">맨투맨</option>
          <option value="B01">코트</option>
          <option value="B02">패딩</option>
          <option value="C01">트레이닝 팬츠</option>
          <option value="C02">숏 팬츠</option>
          <option value="D01">구두</option>
          <option value="D02">운동화</option>
        </select>
        <input
          type="text"
          name="size"
          value={product.size ?? ''}
          placeholder="사이즈(콤마(,)로 구분)"
          required
          onChange={handleChange}
        />
        <button>등록하기</button>
      </form>
    </section>
  );
}
