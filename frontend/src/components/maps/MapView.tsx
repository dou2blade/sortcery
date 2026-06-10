export const MapView = ({ latitude, longitude }: { 
  latitude?: number, 
  longitude?: number
}) => {
  const src = `https://www.google.com/maps?q=${latitude},${longitude}&z=15&output=embed`;

  return (
    <iframe
      width="100%"
      height="300"
      loading="lazy"
      src={src}
    />
  );
}
