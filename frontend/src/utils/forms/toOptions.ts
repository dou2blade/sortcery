export const toOptions = <T>(
  data: T[],
  label: (row: T) => string,
  value: (row: T) => string | number
) => {
  return data.map((item) => ({
    label: label(item),
    value: value(item),
  }));
};
