import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { upload } from '../api/uploader';
import { useQueryClient } from '@tanstack/react-query';

export default function Register() {
  const [product, setProduct] = useState({});
  const [file, setFile] = useState([]);
  const [detail, setDetail] = useState();
  const [isUploading, setIsUploading] = useState(false);
  const navigate = useNavigate();
  const client = useQueryClient();
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
    setIsUploading(true);
    upload(product, file, detail) //
      .then((data) => {
        if (data !== 400) {
          navigate('/admin');
        }
      })
      .finally(() => {
        setIsUploading(false);
        client.invalidateQueries(['products']);
      });
  };
  return (
    <section className="register-section w-full p-8 text-sm">
      <form
        className="flex flex-col lg:w-1/2"
        method="POST"
        encType="multipart/form-data"
        onSubmit={handleSubmit}
      >
        <label htmlFor="imgFiles">제품 이미지</label>
        <input
          id="imgFiles"
          type="file"
          accept="image/*"
          name="imgFiles"
          multiple="multiple"
          required
          onChange={handleChange}
        />
        <label htmlFor="detailInfo">제품 상세 이미지</label>
        <input
          id="detailInfo"
          type="file"
          accept="image/*"
          name="detailInfo"
          required
          onChange={handleChange}
        />
        <label htmlFor="name">제품 이름</label>
        <input
          id="name"
          type="text"
          name="name"
          value={product.name ?? ''}
          placeholder="제품 이름"
          required
          onChange={handleChange}
        />
        <label htmlFor="price">제품 가격</label>
        <input
          id="price"
          type="number"
          name="price"
          min="0"
          value={product.price ?? ''}
          placeholder="가격"
          required
          onChange={handleChange}
        />
        <label htmlFor="code">제품 분류</label>
        <select id="code" name="code" onChange={handleChange}>
          <option value="">---분류---</option>
          <option value="A01">후드 티셔츠</option>
          <option value="A02">맨투맨</option>
          <option value="B01">코트</option>
          <option value="B02">패딩</option>
          <option value="C01">트레이닝 팬츠</option>
          <option value="C02">숏 팬츠</option>
          <option value="D01">구두</option>
          <option value="D02">운동화</option>
        </select>
        <label htmlFor="size">제품 사이즈</label>
        <input
          id="size"
          type="text"
          name="size"
          value={product.size ?? ''}
          placeholder="사이즈(콤마(,)로 구분)"
          required
          onChange={handleChange}
        />
        <button
          className="bg-rose-400 w-28 p-1 text-white rounded-lg"
          disabled={isUploading}
        >
          {isUploading ? '업로드중..' : '제품 등록하기'}
        </button>
      </form>
    </section>
  );
}
