import React from 'react';

export default function UploadImagePreview({ files }) {
  return (
    <div className="flex mb-4">
      {files.length >= 1 &&
        files.map((file) => (
          <img
            key={file.name}
            className="w-36 h-36 object-cover mr-2"
            src={URL.createObjectURL(file)}
            alt={file.name}
          />
        ))}
    </div>
  );
}
