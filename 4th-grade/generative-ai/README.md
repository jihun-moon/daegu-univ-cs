# 생성형 AI

4학년 생성형 AI 수업의 실습 코드입니다. PyTorch 학습 루프를 직접 짜 보는 것으로 시작해서 오토인코더, 변분 오토인코더, GAN 순서로 구현했습니다.

## 폴더 구성

| 경로 | 내용 |
| --- | --- |
| [`codes/`](codes) | 실습 노트북 6개 |

수업에서는 확산 모델까지 다뤘지만, 실습 노트북까지 만든 것은 GAN 까지입니다.

## 노트북

| 파일 | 데이터셋 | 구조 | 학습 설정 | 마지막 기록 |
| --- | --- | --- | --- | --- |
| [FashionMNIST_MLP.ipynb](codes/FashionMNIST_MLP.ipynb) | FashionMNIST | 784-512-512-10 완전연결, ReLU | SGD lr 1e-3, 배치 64, 30에폭 | 테스트 정확도 80.9% |
| [FashionMNIST_CNN.ipynb](codes/FashionMNIST_CNN.ipynb) | FashionMNIST | Conv(1→32)-Pool-Conv(32→64)-Pool-FC(3136→128)-Dropout 0.5-FC(128→10) | SGD lr 1e-3, 배치 64, 10에폭 | 테스트 정확도 77.7% |
| [Denoising_AE.ipynb](codes/Denoising_AE.ipynb) | MNIST | 합성곱 오토인코더, 잠재 차원 4 | Adam lr 1e-3 (weight decay 1e-5), MSE, 배치 128, 30에폭 | 검증 MSE 0.026 |
| [VAE.ipynb](codes/VAE.ipynb) | MNIST | 784-400-400 뒤에 μ, logσ² 두 갈래, 잠재 차원 2, LeakyReLU 0.2 | Adam lr 1e-3, BCE + KL, 배치 100, 30에폭 | 테스트 손실 144.63 |
| [GAN.ipynb](codes/GAN.ipynb) | MNIST | 완전연결 G(128→256→512→1024→784, Tanh), D(784→1024→512→256→1, Dropout 0.3) | Adam lr 2e-4, BCE, 배치 512, 400에폭 | D(x) 0.566, D(G(z)) 0.400 |
| [DCGAN_CIFAR10.ipynb](codes/DCGAN_CIFAR10.ipynb) | CIFAR-10 | ConvTranspose 기반 G(ngf 64), Conv 기반 D(ndf 64), z 차원 128 | Adam betas (0.5, 0.999), G lr 2e-4 / D lr 1e-4, 배치 64, 25에폭 | D(x) 0.954, D(G(z)) 0.504 |

### 분류 모델로 학습 루프 익히기

FashionMNIST 두 개는 생성 모델로 넘어가기 전에 PyTorch 사용법을 익히려고 만든 실습입니다. `train` 과 `test` 함수를 직접 작성하고, 에폭마다 손실과 정확도를 쌓아 곡선으로 그린 뒤 `state_dict` 로 저장했다가 다시 불러오는 것까지 해 봤습니다. 옵티마이저 설정은 그대로 두고 CNN 은 10에폭, MLP 는 30에폭을 돌렸더니 CNN 쪽 정확도가 오히려 낮게 나왔습니다. 구조만 바꾼다고 바로 좋아지지는 않는다는 것을 여기서 봤습니다.

### 잡음 제거 오토인코더

MNIST 입력에 표준정규 잡음을 0.3 배로 더한 뒤 원본을 복원하도록 학습시켰습니다. 인코더는 stride 2 합성곱 3개로 (1,28,28)을 (32,3,3)까지 줄이고 디코더가 `ConvTranspose2d` 로 되돌립니다. 출력이 정확히 28x28 로 돌아오도록 `output_padding` 을 맞추는 게 번거로워서 레이어마다 텐서 크기를 주석으로 적어 두었습니다.

### 변분 오토인코더

재매개변수화 트릭(z = μ + σ·ε)을 직접 구현하고 손실을 복원 항(BCE)과 KL 항으로 나눠 계산했습니다. 잠재 차원을 2로 잡은 덕분에 테스트셋 전체의 μ 를 산점도로 찍어 숫자별로 어떻게 나뉘는지 볼 수 있었고, 잠재 공간을 20x20 격자로 훑어 숫자가 연속적으로 변하는 매니폴드도 그렸습니다.

### GAN

MNIST 를 [-1, 1] 로 정규화하고 생성자 출력에 Tanh 를 써서 범위를 맞췄습니다. 손실값만 봐서는 학습이 되고 있는지 판단하기 어려워서 에폭마다 D(x) 와 D(G(z)) 를 같이 출력했습니다. 400에폭 끝에서 0.566 과 0.400 이었습니다.

### DCGAN

CIFAR-10 컬러 이미지로 옮기면서 완전연결을 전부 합성곱으로 바꿨습니다. 가중치를 정규분포로 초기화하고 Adam beta1 을 0.5 로 두었으며, 판별자 학습률만 생성자의 절반인 1e-4 로 낮췄습니다. 고정 노이즈 25개를 미리 만들어 두고 에폭마다 같은 z 로 샘플을 뽑아 `./data_gen/dcgan_cifar10/img` 에 저장했기 때문에, 에폭이 지나면서 같은 자리의 이미지가 어떻게 변하는지 비교할 수 있었습니다.

## 실행

Jupyter 나 Colab 에서 노트북을 열고 위에서 아래로 실행하면 되며, MNIST 와 CIFAR-10 은 torchvision 이 처음 실행할 때 자동으로 내려받습니다.

## 개인 프로젝트와의 연결

여기서 익힌 PyTorch 학습·추론 루프를 개인 프로젝트에서 그대로 쓰고 있습니다. CHRONO(주식 퀀트 플랫폼)의 모델 추론은 PyTorch 와 ONNX Runtime 으로 돌리고 있고, PII-Guardian 에서는 HuggingFace NER 모델을 파인튜닝했습니다.
