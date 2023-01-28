import axios from 'axios';

export default class FakeProduct {
  constructor() {}

  async popular() {
    return axios.get(`/products/popular.json`).then((res) => res.data);
  }

  async mainProduct() {
    return axios.get(`/products/mainProduct.json`).then((res) => res.data);
  }
}
