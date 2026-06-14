export const MapView = ({ latitude, longitude, className }: { 
  latitude?: number;
  longitude?: number;
  className?: string;
}) => {
  const src = `https://www.google.com/maps?q=${latitude},${longitude}&z=15&output=embed`;

  return (
    <iframe
      width="100%"
      height="300"
      loading="lazy"
      src={src}
      className={className ?? "rounded-xl border border-slate-300"}
    />
  );
}
