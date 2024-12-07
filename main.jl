using WAV
using Statistics

# Load an audio file
y, fs = wavread("example.wav")

# Define feature extraction (e.g., mean amplitude, zero-crossing rate)
function extract_features(audio::Vector{Float64}, sr::Int)
    # Compute features (example: mean amplitude)
    mean_amp = mean(abs.(audio))
    # Add your own features like spectral centroid, MFCC, etc.
    return [mean_amp]
end

features = extract_features(y, fs)

