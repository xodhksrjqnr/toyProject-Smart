export default function emailRegexCheck(email) {
  email instanceof Object && (email = email.email);
  const emailRegex =
    /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/;

  return emailRegex.test(email);
}
