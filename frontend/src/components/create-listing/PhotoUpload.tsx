import React from "react";

type PhotoUploadProps = {
    photo: File | null;
    setPhoto: React.Dispatch<React.SetStateAction<File | null>>;
    existingImageUrl?: string;
};

export default function PhotoUpload({ photo, setPhoto, existingImageUrl }: PhotoUploadProps) {
    const handlePhotoChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const file = e.target.files?.[0];
        if (file) {
            setPhoto(file);
        }
    };

    return (
        <div className="border-2 border-dashed border-gray-300 rounded-lg overflow-hidden relative">
            <input
                type="file"
                id="photo-upload"
                accept="image/*"
                onChange={handlePhotoChange}
                className="hidden"
            />
            <label
                htmlFor="photo-upload"
                className="flex flex-col items-center justify-center p-8 cursor-pointer"
            >
                {photo ? (
                    <img
                        src={URL.createObjectURL(photo)}
                        alt="Selected photo"
                        className="max-w-full max-h-72 object-cover rounded-md"
                    />
                ) : existingImageUrl ? (
                    <img
                        src={existingImageUrl}
                        alt="Existing photo"
                        className="max-w-full max-h-72 object-cover rounded-md"
                    />
                ) : (
                    <div className="flex flex-col items-center gap-2 text-gray-500">
                        <svg
                            className="w-12 h-12"
                            fill="none"
                            stroke="currentColor"
                            strokeWidth="2"
                            viewBox="0 0 24 24"
                            xmlns="http://www.w3.org/2000/svg"
                        >
                            <path
                                strokeLinecap="round"
                                strokeLinejoin="round"
                                d="M16 16l-4-4m0 0l-4 4m4-4v12M20 8a4 4 0 00-8 0M4 16a4 4 0 018 0"
                            />
                        </svg>
                        <span>Select a photo</span>
                    </div>
                )}
            </label>
        </div>
    );
}
