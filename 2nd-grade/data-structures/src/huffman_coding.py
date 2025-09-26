import heapq
import os
from collections import defaultdict, Counter
import argparse
import json

class HuffmanNode:
    def __init__(self, char, freq):
        self.char = char
        self.freq = freq
        self.left = None
        self.right = None

    def __lt__(self, other):
        return self.freq < other.freq

def build_huffman_tree(text):
    frequency = Counter(text)
    priority_queue = [HuffmanNode(char, freq) for char, freq in frequency.items()]
    heapq.heapify(priority_queue)

    while len(priority_queue) > 1:
        left = heapq.heappop(priority_queue)
        right = heapq.heappop(priority_queue)
        merged = HuffmanNode(None, left.freq + right.freq)
        merged.left = left
        merged.right = right
        heapq.heappush(priority_queue, merged)

    return priority_queue[0]

def build_codes(node, prefix="", codebook={}):
    if node:
        if node.char is not None:
            codebook[node.char] = prefix
        build_codes(node.left, prefix + "0", codebook)
        build_codes(node.right, prefix + "1", codebook)
    return codebook

def compress(text):
    root = build_huffman_tree(text)
    huffman_codes = build_codes(root)
    
    encoded_text = ''.join(huffman_codes[char] for char in text)
    padded_encoded_text = pad_encoded_text(encoded_text)
    byte_array = get_byte_array(padded_encoded_text)
    
    return byte_array, huffman_codes, root

def pad_encoded_text(encoded_text):
    extra_padding = 8 - len(encoded_text) % 8
    for i in range(extra_padding):
        encoded_text += "0"
    padded_info = "{0:08b}".format(extra_padding)
    encoded_text = padded_info + encoded_text
    return encoded_text

def get_byte_array(padded_encoded_text):
    if len(padded_encoded_text) % 8 != 0:
        print("Encoded text not padded properly")
        exit(0)
    
    byte_array = bytearray()
    for i in range(0, len(padded_encoded_text), 8):
        byte = padded_encoded_text[i:i+8]
        byte_array.append(int(byte, 2))
    return byte_array

def decompress(byte_array, root):
    bit_string = ""
    for byte in byte_array:
        bit_string += f"{byte:08b}"

    padded_info = bit_string[:8]
    extra_padding = int(padded_info, 2)
    
    bit_string = bit_string[8:]
    encoded_text = bit_string[:-extra_padding]

    decoded_text = decode_text(encoded_text, root)
    return decoded_text

def decode_text(encoded_text, root):
    current_node = root
    decoded_text = ""
    
    for bit in encoded_text:
        if bit == '0':
            current_node = current_node.left
        else:
            current_node = current_node.right

        if current_node.char is not None:
            decoded_text += current_node.char
            current_node = root

    return decoded_text

def save_compressed_file(byte_array, codes, output_path):
    # 허프만 코드를 파일 시작 부분에 추가
    with open(output_path, 'wb') as file:
        codes_json = json.dumps(codes).encode('utf-8')
        codes_len = len(codes_json)
        file.write(codes_len.to_bytes(4, 'big'))
        file.write(codes_json)
        file.write(bytes(byte_array))

def load_compressed_file(file_path):
    with open(file_path, 'rb') as file:
        # 파일 시작 부분에서 허프만 코드 읽기
        codes_len = int.from_bytes(file.read(4), 'big')
        codes_json = file.read(codes_len)
        huffman_codes = json.loads(codes_json.decode('utf-8'))
        
        byte_array = bytearray(file.read())
    return byte_array, huffman_codes

def main():
    parser = argparse.ArgumentParser(description="Huffman Coding Compression and Decompression")
    parser.add_argument('mode', choices=['compress', 'decompress'], help="Mode: compress or decompress")
    parser.add_argument('input_file', help="Input file path")
    parser.add_argument('output_file', help="Output file path")

    args = parser.parse_args()

    if args.mode == 'compress':
        with open(args.input_file, 'r', encoding='utf-8') as file:
            text = file.read()

        byte_array, huffman_codes, root = compress(text)
        save_compressed_file(byte_array, huffman_codes, args.output_file)

    elif args.mode == 'decompress':
        byte_array, huffman_codes = load_compressed_file(args.input_file)
        root = build_huffman_tree_from_codes(huffman_codes)

        decompressed_text = decompress(byte_array, root)
        with open(args.output_file, 'w', encoding='utf-8') as file:
            file.write(decompressed_text)

def build_huffman_tree_from_codes(huffman_codes):
    root = HuffmanNode(None, 0)
    for char in huffman_codes:
        code = huffman_codes[char]
        current_node = root
        for bit in code:
            if bit == '0':
                if not current_node.left:
                    current_node.left = HuffmanNode(None, 0)
                current_node = current_node.left
            else:
                if not current_node.right:
                    current_node.right = HuffmanNode(None, 0)
                current_node = current_node.right
        current_node.char = char
    return root

if __name__ == "__main__":
    main()

# 터미널에서 파이썬 파일에 있는 곳에 지정 한 후 밑에 있는 명령어 실행하면 된다.

# 파일 압축
# python huffman_coding.py compress <input_file> <output_file>

# 파일 복원
# python huffman_coding.py decompress <compressed_file> <output_file>
