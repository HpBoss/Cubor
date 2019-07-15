import os
from typing import List

from pypinyin import pinyin, lazy_pinyin

# map vowel-number combination to unicode
toneMap = {
    "d": ['ā', 'ē', 'ī', 'ō', 'ū', 'ǜ'],
    "f": ['á', 'é', 'í', 'ó', 'ú', 'ǘ'],
    "j": ['ǎ', 'ě', 'ǐ', 'ǒ', 'ǔ', 'ǚ'],
    "k": ['à', 'è', 'ì', 'ò', 'ù', 'ǜ'],
}

weightMap = {}


def getWeightMap():
    with open(os.path.join(os.path.dirname(__file__),
                           "../cells/cubor-base.dict.yaml"), "r", encoding='utf8') as base:
        _lines = base.readlines()
        for wunit in _lines:
            units = wunit.split('\t')
            if len(units) == 3 and len(units[0]) == 1:  # 保证不是空行、且为单字，并一定能得到权重
                units[2] = units[2][:-1]
                if weightMap.get(units[0]) is None:
                    weightMap.__setitem__(units[0], units[2])
                else:  # 说明权重字典中已经有了该词
                    if int(units[2]) > int(weightMap.get(units[0])):  # 如果权重更大则更新，我们的声调码 权重要高！
                        weightMap.__setitem__(units[0], units[2])


def flatten(a):
    for each in a:
        if not isinstance(each, list):
            yield each
        else:
            yield from flatten(each)


def getPinyins(c: List[List[str]]) -> list:
    return list(flatten(pinyin(c, heteronym=True)))


def getToneKeys(tone: str) -> str:
    for toneKey, toneList in toneMap.items():
        for toneChar in toneList:
            if tone.__contains__(toneChar):
                return toneKey
    return 'l'


def readSingleCharYaml():
    retlines = []
    with open(os.path.join(os.path.dirname(__file__),
                           "../cubor/cubor-single.dict.yaml"), "r", encoding='utf8') as yaml:
        line = yaml.readline()

        # 准备追加音调
        while len(line) is not 0:
            chars = line.split('\t')
            if len(chars) is 3:
                # 获得词语内容、本来的读音
                word, srcPy = chars[0], chars[1]
                tones = getPinyins(pinyin(word, heteronym=True))

                tempToneKeys = []
                for tone in tones:
                    toneKey = getToneKeys(tone)
                    if toneKey not in tempToneKeys:  # 如果类似 啊 这样的字有多音 a e 都是一声就避免重复
                        tempToneKeys.append(toneKey)

                        # base-dict 来源于 pinyin_simp 如果按简体字表配置的权重Map 中找不到，则说明是权重很低的繁体字
                        weight = weightMap.get(word) if weightMap.get(word) is not None else '0'

                        line = f'{word}\t{srcPy}{toneKey}\t{weight}\r\n'  # 部署时用
                        # line = f'{word}\t{toneKey}\t{weight}\r\n'             # 重构单字声调码表用
                        retlines.append(line)

                        # fullPinyin = lazy_pinyin(word)[0] if len(lazy_pinyin(word)) > 0 else ''
                        # if len(fullPinyin) > 0 and fullPinyin != srcPy:
                        #     retlines.append(f'{word}\t{fullPinyin}{toneKey}\t{weight}\r\n')     # 部署时用

                line = yaml.readline()

    with open(os.path.join(os.path.dirname(__file__),
                           "../cells/cubor-base.dict.yaml"), "a", encoding='utf8') as writer:  # 部署时用
        # "../cubor/cubor-tones.dict.yaml"), "w", encoding='utf8') as writer:   # 重构单字码表时用
        writer.writelines(retlines)


if __name__ == '__main__':
    print('[INFO] - 构建库珀输入法 声调表...')
    getWeightMap()  # 先找到权重表
    readSingleCharYaml()  # 再写入声调码表
    print('[INFO] - 构建完成！')
