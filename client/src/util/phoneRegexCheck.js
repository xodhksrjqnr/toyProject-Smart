export default function phoneRegexCheck(number) {
  const numberRegex = /^010-[0-9]{4}-[0-9]{4}$/;
  return numberRegex.test(number);
}
