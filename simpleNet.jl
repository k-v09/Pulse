using Flux

# Define the model
model = Chain(
    Dense(30, 64, relu),  # Input size matches feature vector length
    Dense(64, 32, relu),
    Dense(32, 4),         # 4 mood categories
    softmax
)

# Loss function and optimizer
loss(x, y) = Flux.crossentropy(model(x), y)
opt = ADAM()

# Example dummy data
X = rand(Float32, 30, 100)  # 100 samples, 30 features each
y = Flux.onehotbatch(rand(1:4, 100), 1:4)

# Training
Flux.train!(loss, params(model), [(X, y)], opt)

