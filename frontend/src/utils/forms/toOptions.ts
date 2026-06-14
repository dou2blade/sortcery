export const toOptions = <T>(
  data: T[],
  label: (row: T) => string,
  value: (row: T) => number
) => {
  return data.map((item) => ({
    label: label(item),
    value: value(item),
  }));
};
